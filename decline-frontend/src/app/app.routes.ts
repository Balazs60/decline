import { Routes } from '@angular/router';
import { TaskComponent } from './task/task.component';
import { AboutComponent } from './about/about/about.component';

export const routes: Routes = [
    {
        path: '',
        component: TaskComponent
      },
      {
        path: 'about',
        component: AboutComponent
      }
];
