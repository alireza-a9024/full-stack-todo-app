import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  
  private apiUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) {}

  getTasks(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  createTask(task: any): Observable<any> {
    return this.http.post(this.apiUrl, task);
  }

  
  updateTaskTitle(id: string, title: string): Observable<any> {
   
    const url = `${this.apiUrl}/${id}/title`;
    const body = {title: title}
    return this.http.patch(url, body)
  }

 
  updateTaskStatus(id: string, status: string): Observable<any> {
    const url = `${this.apiUrl}/${id}/status`;
    const body = {status: status}
    return this.http.patch(url, body)
  }

  deleteTask(id: string): Observable<any> {
    
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
  }
}
