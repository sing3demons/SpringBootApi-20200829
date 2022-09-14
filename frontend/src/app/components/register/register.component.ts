import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { IRegisterRequest } from 'src/app/interfaces/register-request';
import { UserService } from 'src/app/services/user-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerFormGroup: FormGroup = new FormGroup({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
  });

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {}

  onSubmit() {
    if (this.registerFormGroup.invalid) return;

    let name = this.registerFormGroup.controls.name.value;
    let email = this.registerFormGroup.controls.email.value;
    let password = this.registerFormGroup.controls.password.value;

    let response = this.userService
      .register(email, password, name)
      .subscribe(({ name, email }) => {
        alert(name);
        this.router.navigate(['/login']);
      });
  }
}
