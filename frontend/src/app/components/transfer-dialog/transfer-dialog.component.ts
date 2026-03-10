import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Beneficio } from '../../services/beneficio.service';

export interface TransferDialogData {
  beneficioDe: Beneficio;
  beneficios: Beneficio[];
}

@Component({
  selector: 'app-transfer-dialog',
  templateUrl: './transfer-dialog.component.html',
  styleUrls: ['./transfer-dialog.component.scss']
})
export class TransferDialogComponent implements OnInit {
  form: FormGroup;
  saldoDisponivel: number = 0;

  constructor(
    public dialogRef: MatDialogRef<TransferDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: TransferDialogData,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      destinoId: ['', Validators.required],
      valor: [0, [Validators.required, Validators.min(0.01)]]
    });
  }

  ngOnInit(): void {
    this.saldoDisponivel = this.data.beneficioDe.valor;
    
    const valorControl = this.form.get('valor');
    if (valorControl) {
      valorControl.valueChanges.subscribe((valor) => {
        if (valor > this.saldoDisponivel) {
          valorControl.setErrors({ 'insufficient': true });
        }
      });
    }
  }

  get beneficiosDisponiveis(): Beneficio[] {
    return this.data.beneficios.filter(b => b.id !== this.data.beneficioDe.id);
  }

  obterNomeBeneficio(id: number): string {
    const beneficio = this.data.beneficios.find(b => b.id === id);
    return beneficio ? beneficio.nome : '';
  }

  transferir(): void {
    if (this.form.invalid) return;

    const destinoIdControl = this.form.get('destinoId');
    const valorControl = this.form.get('valor');
    
    const destinoId = destinoIdControl ? parseInt(destinoIdControl.value) : 0;
    const valor = valorControl ? valorControl.value : 0;

    this.dialogRef.close({
      destinoId,
      valor
    });
  }

  cancelar(): void {
    this.dialogRef.close();
  }
}
