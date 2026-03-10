import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BeneficioComponent } from './components/beneficio/beneficio.component';


const routes: Routes = [
  { path: 'beneficios', component: BeneficioComponent },
  { path: '', redirectTo: '/beneficios', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
