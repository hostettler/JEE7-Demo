import {Component, Input} from 'angular2/core';
import {Student} from './student';
import {NgForm}    from 'angular2/common';
@Component({
    selector: 'student-detail',
    templateUrl: 'app/student-detail.component.html'
})
export class StudentDetailComponent {
    @Input()  student : Student;

}