import {Component, Input, Output, EventEmitter} from 'angular2/core';
import {Student} from './student';
import {NgForm}    from 'angular2/common';
import {MyDatePicker} from './date-picker/mydatepicker';
import {MyDate} from './date-picker/interfaces';
import {StudentService} from './student.service';

@Component({
    selector: 'student-detail',
    templateUrl: 'app/student-detail.component.html',
    directives: [MyDatePicker],
    providers: [StudentService]
})

export class StudentDetailComponent {
    @Output() onRefreshEmitter = new EventEmitter();
            
    private _student: Student;
    selectedDate:MyDate;

    constructor(private _studentService: StudentService) {
    }
    
    @Input()
    set student(student: Student) {
        console.log('set' + student);
        if (student != null) {
            this._student = student;
            this.selectedDate = {
                day: student.birthDate.getDate(),
                month: student.birthDate.getMonth() + 1,
                year: student.birthDate.getFullYear()
            }
        }
    }

    get student() { return this._student; }


    onDateChanged($event) {
        console.log($event);
        this._student.birthDate=new Date( $event.date.year,  $event.date.month,$event.date.day);
    }
       
    private myDatePickerOptions = {
        todayBtnTxt: 'Today',
        dateFormat: 'dd.mm.yyyy',
        firstDayOfWeek: 'mo',
        sunHighlight: true,
        height: '34px',
        width: '260px'
    };

    onSubmit() {
        console.log(this._student);
        this._studentService.update(this._student);
        this._studentService.getStudents().then(students => console.log(students[0]));
        
        this.onRefreshEmitter.next(null);
    }
}