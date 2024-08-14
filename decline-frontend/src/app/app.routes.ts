import { Routes } from '@angular/router';
import { TaskComponent } from './task/task.component';
import { AboutComponent } from './about/about/about.component';
import { RegisterComponent } from './register/register/register.component';
import { LoginComponent } from './login/login/login.component';
import { ProfileComponent } from './profile/profile/profile.component';
import { authGuard } from './services/guard/auth.guard';

export const routes: Routes = [
    {
        path: '',
        component: TaskComponent
      },
      {
        path: 'about',
        component: AboutComponent
      },
        {
          path: 'register',
          component: RegisterComponent
        },
        {
          path: 'login',
          component: LoginComponent
        },
        {
          path: 'profile',
          component: ProfileComponent,
          canActivate: [authGuard]
        }
      
];
