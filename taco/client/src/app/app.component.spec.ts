import { TestBed, async } from '@angular/core/testing';

import { BrowserModule } from '@angular/platform-browser';

import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NonWrapsPipe } from './recents/NonWrapsPipe';
import { WrapsPipe } from './recents/WrapsPipe';
import { DesignComponent } from './design/design.component';
import { HttpClientModule } from '@angular/common/http';

import { CartComponent } from './cart/cart.component';
import { RecentsComponent } from "./recents/recents.component";
import { CloudTitleComponent } from "./cloud-title/cloud-title.component";
import { LocationComponent } from "./location/location.component";
import { SpecialComponent } from "./special/special.component";
import { GroupBoxComponent} from "./group-box/group-box.component";
import { BigButtonComponent} from "./big-button/big-button.component";
import { LittleButtonComponent} from "./little-button/little-button.component";
import { routes } from "./app.routes";
import { APP_BASE_HREF } from "@angular/common";
import { ApiService } from "./api/api.service";
import { CartService } from "./cart/cart.service";
import { RecentTacosService } from "./recents/recents.service";

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        HeaderComponent,
        HomeComponent,
        LoginComponent,
        FooterComponent,
        RecentsComponent,
        SpecialComponent,
        LocationComponent,
        CloudTitleComponent,
        DesignComponent,
        CartComponent,
        NonWrapsPipe,
        WrapsPipe,
        GroupBoxComponent,
        BigButtonComponent,
        LittleButtonComponent,
      ],
      imports: [
        RouterModule.forRoot(routes),
        BrowserModule,
        HttpClientModule,
        FormsModule,
      ],
      providers: [
        {provide: APP_BASE_HREF, useValue: '/'},
        ApiService,
        CartService,
        RecentTacosService,
      ]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
  it(`should have as title 'app'`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('Taco Cloud');
  }));
});
