import { inject, Inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {

  const router = inject(Router)

  const localData = localStorage.getItem('token');
  if (localData) {
    return true;
  } else {
    router.navigateByUrl('/login');
    return false;
  }
};
