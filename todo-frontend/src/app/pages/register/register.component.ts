import { Component } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-register',
  imports: [RouterModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ) {
    this.form = this.fb.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      firstname: new FormControl('', [Validators.required]),
      lastname: new FormControl('', [Validators.required]),
    });
  }

  onSubmit() {
  
    if (this.form.valid) {
      this.authService.register(this.form.value).subscribe({
        next: () => {
          alert('Registration successful! You can now log in.');
          this.router.navigate(['/login']);
        },
        error: (err) => {
          alert('a user wih this email address already exists.');
          console.error('Registration error:', err);
        },
      });
    } else {
      this.form.markAllAsTouched();
    }
  }
}
