import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Beneficio, BeneficioService } from '../../services/beneficio.service';

@Component({
  selector: 'app-beneficio',
  templateUrl: './beneficio.component.html',
  styleUrls: ['./beneficio.component.scss']
})
export class BeneficioComponent implements OnInit {
  beneficios: Beneficio[] = [];
  form: FormGroup;
  editId: number | null = null;

  constructor(private service: BeneficioService, private fb: FormBuilder) {
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
    this.service.listar().subscribe(data => this.beneficios = data);
  }

  editar(beneficio: Beneficio) {
    this.editId = beneficio.id!;
    this.form.patchValue(beneficio);
  }

  salvar() {
    if (this.form.invalid) return;

    const dados: Beneficio = this.form.value;
    if (this.editId) {
      this.service.atualizar(this.editId, dados).subscribe(() => {
        this.listar();
        this.form.reset({ ativo: true });
        this.editId = null;
      });
    } else {
      this.service.criar(dados).subscribe(() => {
        this.listar();
        this.form.reset({ ativo: true });
      });
    }
  }

  deletar(id: number) {
    if (confirm('Deseja realmente deletar este benefício?')) {
      this.service.deletar(id).subscribe(() => this.listar());
    }
  }

  cancelar() {
    this.form.reset({ ativo: true });
    this.editId = null;
  }
}