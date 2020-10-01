
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreateCurrencyComponent } from './create-currency/create-currency.component';
import { CurrencyHistoryComponent } from './currency-history/currency-history.component';
import { CurrencyListComponent } from './currency-list/currency-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'currencies', pathMatch: 'full' },
  { path: 'currencies', component: CurrencyListComponent },
  { path: 'currency', component: CreateCurrencyComponent },
  { path: 'currencyHistory/:code/:dtFrom/:dtTo', component: CurrencyHistoryComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }