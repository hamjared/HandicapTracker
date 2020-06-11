import { Component } from '@angular/core';
import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent {
  tableColumns : string[] = ['course', 'tees', 'score'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);
}

export interface Element {
  course: string;
  tees: string;
  score: number;
}

const ELEMENT_DATA: Element[] = [
  {course: "Highland Meadows", tees: 'Blue', score: 82},
  {course: "Southridge", tees: 'Blue', score: 82},
  {course: "City Park", tees: 'Blue', score: 82}
];
