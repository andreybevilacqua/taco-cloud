import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BigButtonComponent } from './big-button/big-button.component';
import { CartComponent } from './cart/cart.component';
import { CloudTitleComponent } from './cloud-title/cloud-title.component';
import { DesignComponent } from './design/design.component';
import { FooterComponent } from './footer/footer.component';
import { GroupBoxComponent } from './group-box/group-box.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { LittleButtonComponent } from './little-button/little-button.component';
import { LocationComponent } from './location/location.component';
import { LoginComponent } from './login/login.component';
import { RecentsComponent } from './recents/recents.component';
import { SpecialComponent } from './special/special.component';
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { routes } from "./app.routes";
import { ApiService } from "./api/api.service";
import { CartService } from "./cart/cart.service";
import { RecentTacosService } from "./recents/recents.service";
import { OAuthModule } from "angular-oauth2-oidc";
import { NonWrapsPipe } from "./recents/NonWrapsPipe";
import { WrapsPipe } from "./recents/WrapsPipe";

@NgModule({
  declarations: [
    AppComponent,
    BigButtonComponent,
    CartComponent,
    CloudTitleComponent,
    DesignComponent,
    FooterComponent,
    GroupBoxComponent,
    HeaderComponent,
    HomeComponent,
    LittleButtonComponent,
    LocationComponent,
    LoginComponent,
    RecentsComponent,
    SpecialComponent,
    NonWrapsPipe,
    WrapsPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    OAuthModule.forRoot()
  ],
  providers: [ApiService, CartService, RecentTacosService],
  bootstrap: [AppComponent]
})
export class AppModule { }
