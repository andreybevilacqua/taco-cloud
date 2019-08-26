import { HomeComponent } from './home/home.component';
import { Routes } from '@angular/router';
import { RecentsComponent } from "./recents/recents.component";
import { DesignComponent } from './design/design.component';
import { CartComponent } from './cart/cart.component';
import { LoginComponent } from './login/login.component';
import { SpecialComponent } from "./special/special.component";
import { LocationComponent } from "./location/location.component";

export const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'recents',
    component: RecentsComponent
  },
  {
    path: 'specials',
    component: SpecialComponent
  },
  {
    path: 'locations',
    component: LocationComponent
  },
  {
    path: 'design',
    component: DesignComponent
  },
  {
    path: 'cart',
    component: CartComponent
  },
  {
    path: '**',
    redirectTo: 'home',
    pathMatch: 'full'
  },
];
