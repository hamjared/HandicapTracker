import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent implements OnInit {
  private password:string;
  private userName:string;

  constructor(private router: Router) {
 }

  ngOnInit() {
    console.log("Hello");
  }

  login(){
    console.log("Login");
    console.log(this.userName);
    console.log(this.password);
    this.router.navigate(['/userProfile'])

  }

  passwordChanged(event){
    this.password = event.target.value;
  }

  userNameChanged(event){
    this.userName = event.target.value;
  }

}
