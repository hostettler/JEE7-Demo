import {Injectable} from 'angular2/core';
import {Student} from './student';



let STUDENTS: Student[] = [
  { "id": 11, "lastName": "Mr. Nice", "firstName" : "John", "birthDate" : new Date("01.01.1970")},
  { "id": 12, "lastName": "Narco" , "firstName" : "John", "birthDate" :  new Date("01.01.1970")},
  { "id": 13, "lastName": "Bombasto" , "firstName" : "John", "birthDate" :  new Date("01.01.1970")},
  { "id": 14, "lastName": "Celeritas" , "firstName" : "John", "birthDate" :  new Date("01.01.1970")},
  { "id": 15, "lastName": "Magneta" , "firstName" : "John", "birthDate" :  new Date("01.01.1970")},
  { "id": 16, "lastName": "RubberMan" , "firstName" : "John", "birthDate" :  new Date("01.01.1970")},
  { "id": 17, "lastName": "Dynama" , "firstName" : "John", "birthDate" :  new Date("01.01.1970")},
  { "id": 18, "lastName": "Dr IQ" , "firstName" : "John", "birthDate" :  new Date("01.01.1970")},
  { "id": 19, "lastName": "Magma" , "firstName" : "John", "birthDate" :  new Date("01.01.1970")},
  { "id": 20, "lastName": "Tornado" , "firstName" : "John", "birthDate" :  new Date("01.01.1970")}
];

@Injectable()
export class StudentService {
  public getStudents() : Promise<Student[]>{
        return new Promise<Student[]>(resolve =>
        setTimeout(()=>resolve(STUDENTS), 2000));// 2 seconds
  }
}