import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import { Task } from '../../types';

@Injectable({
  providedIn: 'root'
})
export class TasksService {

  constructor(private apiService: ApiService) { }

  getTask = (url: string): Observable<Task> => {
    return this.apiService.get(url)
  }
}
