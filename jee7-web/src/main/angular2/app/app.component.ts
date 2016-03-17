import {Component} from 'angular2/core';
import {Student} from './student';
import {StudentsGridComponent} from './students-grid.component';
import {StudentDetailComponent} from './student-detail.component';
import {StudentService} from './student.service';
import {OnInit} from 'angular2/core';

@Component({
    selector: 'my-app',
    directives: [StudentDetailComponent,StudentsGridComponent],
    styles: [`
  .selected {
    background-color: #CFD8DC !important;
    color: white;
  }
  .students {
    margin: 0 0 2em 0;
    list-style-type: none;
    padding: 0;
    width: 10em;
  }
  .students li {
    cursor: pointer;
    position: relative;
    left: 0;
    background-color: #EEE;
    margin: .5em;
    padding: .3em 0;
    height: 1.6em;
    border-radius: 4px;
  }
  .students li.selected:hover {
    background-color: #BBD8DC !important;
    color: white;
  }
  .students li:hover {
    color: #607D8B;
    background-color: #DDD;
    left: .1em;
  }
  .students .text {
    position: relative;
    top: -3px;
  }
  .students .badge {
    display: inline-block;
    font-size: small;
    color: white;
    padding: 0.8em 0.7em 0 0.7em;
    background-color: #607D8B;
    line-height: 1em;
    position: relative;
    left: -1px;
    top: -4px;
    height: 1.8em;
    margin-right: .8em;
    border-radius: 4px 0 0 4px;
  }
`],

    templateUrl: 'app/app.component.html'
})

export class AppComponent {
    private selectedStudent : Student;
    
    onSelectEmitter(data) {
        this.selectedStudent = data;
    } 
}
