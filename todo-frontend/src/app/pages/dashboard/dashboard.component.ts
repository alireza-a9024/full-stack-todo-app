import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { TaskService } from '../../core/services/task.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule], 
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit {
  tasks: any[] = [];
  editingTaskId: number | null = null;
  editingTaskTitle: string = '';
  //userEmail: string = '';
  editInput: any;

  constructor(
    private authService: AuthService,
    private router: Router,
    private taskService: TaskService,
    private cdr: ChangeDetectorRef,
  ) {}

  fullName: String = '';
  ngOnInit(): void {
    //this.userEmail = this.authService.getUserEmail();
    this.fullName = localStorage.getItem('full_name') || 'User'; 
    this.loadTasks();
  }

  
  loadTasks() {
    this.taskService.getTasks().subscribe({
      next: (data) => {
        this.tasks = data;
        this.cdr.detectChanges(); 
      },
      error: (err) => console.error('Failed to load tasks', err),
    });
  }

  
  addTask(input: HTMLInputElement) {
    const title = input.value.trim();
    if (title) {
      const newTask = {
        title: title,
        status: 'NULL'
      }
      this.taskService.createTask(newTask).subscribe({
        next: (savedTask) => {
          input.value = ''; 

          this.loadTasks();

          //for optimistic ui instead of loading from db
          //this.tasks.push(savedTask);
          //this.cdr.detectChanges(); 
        },
        error: (err) => console.error('Failed to save task', err),
      });
    }
  }

  
  toggleDone(task: any) {
    
    
    
    const newStatus = task.status === 'DONE' ? 'UNDONE' : 'DONE';

    const previousStatus = task.status;
    task.status = newStatus;

    this.taskService.updateTaskStatus(task.id, newStatus).subscribe({
      next: () => {

        this.loadTasks();

        //for optimistic
        //task.completed = isNowCompleted; 
        //this.cdr.detectChanges(); 
      },
      error: (err) => {
        alert('Toggle failed! Check the console.');
        console.error('Toggle error:', err);
      },
    });
  }

  
  deleteTask(id: string) {
    if (confirm('Are you sure you want to delete this task?')) {
      this.taskService.deleteTask(id).subscribe({
        next: (response) => {
          
          this.loadTasks();

          //for optimistic ui instead of loading from db 
          //this.tasks = this.tasks.filter((task: any) => task.id !== id);
          //this.cdr.detectChanges();
        },
        error: (err) => {
          alert('Delete failed! Check the browser console.');
          console.error('Delete error:', err);
        },
      });
    }
  }

  startEdit(task: any) {
    this.editingTaskId = task.id;
    this.editingTaskTitle = task.title;
  }

  onEditInput(event: Event) {
    this.editingTaskTitle = (event.target as HTMLInputElement).value;
  }

  
  saveEdit(task: any) {
    const newTitle = this.editingTaskTitle.trim();

    if (!newTitle) return;

    
    this.taskService.updateTaskTitle(task.id, newTitle).subscribe({
      next: () => {

        

        //for oprimistic
        //task.title = newTitle; 
        this.editingTaskId = null; 
        //this.cdr.detectChanges();
        this.loadTasks();
      },
      error: (err) => {
        alert('Update failed! Check the console.');
        console.error('Save error:', err);
      },
    });
  }

  
  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
