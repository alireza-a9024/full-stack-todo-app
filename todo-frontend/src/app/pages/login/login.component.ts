import { Component } from '@angular/core';
import {
  Form,
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,// TODO: delete this 
  imports: [RouterModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  form: FormGroup;
  //errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ) {
    this.form = this.fb.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this.authService.login(this.form.value).subscribe({

        next: (response: any) => {
          
          if (response && response.firstname && response.lastname) {
            const fullName = response.firstname + ' ' + response.lastname;
            localStorage.setItem('full_name', fullName);
          } else {
            
            localStorage.setItem('full_name', 'Dear User');
          }

          
          this.router.navigate(['/dashboard']);
        },
        error: (err) => {
          alert('Invalid email or password. Please try again.');
          console.error('Login Error:', err);
        },
      });
    } else {
      this.form.markAllAsTouched();
    }
  }
}
