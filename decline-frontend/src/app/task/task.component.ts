import { Component, OnInit } from '@angular/core';
import { TasksService } from '../services/tasks.service';
import { Task } from '../../types';
import { CommonModule } from '@angular/common';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-task',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent {

  task: Task = {
    task: "",
    inflectedAdjective: "",
    inflectedArticle: "",
    articleAnswerOptions: [],
    adjectiveAnswerOptions: []
  }

  constructor(private taskService: TasksService) {}

  ngOnInit() {
    console.log("ngOnInit");
    this.taskService.getTask('/api/task').subscribe((task: Task) => {
      console.log(task.task);
      this.task = task
    });
  }
}
