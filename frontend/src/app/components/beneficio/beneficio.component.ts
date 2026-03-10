import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Beneficio, BeneficioService } from '../../services/beneficio.service';
import { NotificationService } from '../../services/notification.service';
import { TransferDialogComponent } from '../transfer-dialog';

@Component({
  selector: 'app-beneficio',
  templateUrl: './beneficio.component.html',
  styleUrls: ['./beneficio.component.scss']
})
export class BeneficioComponent implements OnInit {
  beneficios: Beneficio[] = [];
  form: FormGroup;
  editId: number | null = null;
  displayedColumns: string[] = ['nome', 'descricao', 'valor', 'ativo', 'acoes'];

  constructor(
    private service: BeneficioService,
    private fb: FormBuilder,
    private notificationService: NotificationService,
    private dialog: MatDialog
  ) {
    this.form = this.fb.group({
      nome: ['', Validators.required],
      descricao: [''],
      valor: [0, [Validators.required, Validators.min(0)]],
      ativo: [true]
    });
  }

  ngOnInit() {
    this.listar();
  }

  listar() {
    this.service.listar().subscribe(
      data => this.beneficios = data,
      error => this.notificationService.error('Erro', 'Falha ao carregar benefícios. Tente novamente.')
    );
  }

  editar(beneficio: Beneficio) {
    this.editId = beneficio.id!;
    this.form.patchValue(beneficio);
  }

  salvar() {
    if (this.form.invalid) return;

    const dados: Beneficio = this.form.value;
    if (this.editId) {
      this.service.atualizar(this.editId, dados).subscribe(
        () => {
          this.notificationService.success('Sucesso!', 'Benefício atualizado com sucesso.');
          this.listar();
          this.form.reset({ ativo: true });
          this.editId = null;
        },
        error => this.notificationService.error('Erro', 'Falha ao atualizar benefício. Tente novamente.')
      );
    } else {
      this.service.criar(dados).subscribe(
        () => {
          this.notificationService.success('Sucesso!', 'Benefício criado com sucesso.');
          this.listar();
          this.form.reset({ ativo: true });
        },
        error => this.notificationService.error('Erro', 'Falha ao criar benefício. Tente novamente.')
      );
    }
  }

  deletar(id: number) {
    const dialogResult = confirm('Deseja realmente deletar este benefício?');
    if (dialogResult) {
      this.service.deletar(id).subscribe(
        () => {
          this.notificationService.success('Sucesso!', 'Benefício deletado com sucesso.');
          this.listar();
        },
        error => this.notificationService.error('Erro', 'Falha ao deletar benefício. Tente novamente.')
      );
    }
  }

  cancelar() {
    this.form.reset({ ativo: true });
    this.editId = null;
  }

  transferir(beneficio: Beneficio) {
    const dialogRef = this.dialog.open(TransferDialogComponent, {
      width: '400px',
      maxWidth: '90vw',
      data: {
        beneficioDe: beneficio,
        beneficios: this.beneficios
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.service.transferir(beneficio.id!, result.destinoId, result.valor).subscribe(
          (response) => {
            this.notificationService.success(
              'Sucesso!',
              `Transferência de ${response.origem.nome} para ${response.destino.nome} realizada com sucesso.`
            );
            this.listar();
          },
          error => this.notificationService.error('Erro', 'Falha ao realizar transferência. Tente novamente.')
        );
      }
    });
  }
}