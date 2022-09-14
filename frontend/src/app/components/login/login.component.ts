import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AppCookieService } from 'src/app/services/app-cookie.service';
import { UserService } from 'src/app/services/user-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginFormGroup: FormGroup = new FormGroup({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  });
  constructor(
    private userService: UserService,
    private appCookieService: AppCookieService,
    private router: Router
  ) {}

  ngOnInit(): void {}
  onSubmit() {
    if (this.loginFormGroup.invalid) {
      return;
    }

    let email = this.loginFormGroup.controls.email.value;
    let password = this.loginFormGroup.controls.password.value;

    this.userService.login(email, password).subscribe(
      ({ token }) => {
        this.appCookieService.setAccessToken(token);
        this.router.navigate(['dashboard']);
      },
      ({ error }) => alert(error.error)
    );
  }
}
