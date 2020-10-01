import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { CreateCurrencyComponent } from './create-currency/create-currency.component';
import { CurrencyListComponent } from './currency-list/currency-list.component';
import { CurrencyDetailsComponent } from './currency-details/currency-details.component';
import { CurrencyCalculatorComponent } from './currency-calculator/currency-calculator.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateCurrencyComponent,
    CurrencyListComponent,
    CurrencyDetailsComponent,
    CurrencyCalculatorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }