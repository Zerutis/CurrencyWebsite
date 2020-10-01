
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreateCurrencyComponent } from './create-currency/create-currency.component';
import { CurrencyCalculatorComponent } from './currency-calculator/currency-calculator.component';
import { CurrencyDetailsComponent } from './currency-details/currency-details.component';
import { CurrencyListComponent } from './currency-list/currency-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'currencies', pathMatch: 'full' },
  { path: 'currencies', component: CurrencyListComponent },
  { path: 'currency', component: CreateCurrencyComponent },
  { path: 'currency-calculator', component:CurrencyCalculatorComponent },
  { path: 'currency-details', component: CurrencyDetailsComponent},
  { path: 'currency-details/:code/:dtFrom/:dtTo', component: CurrencyDetailsComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }