const React = require('react');
const ReactDOM = require('react-dom');
//const client = require('./client');
const textForm = require('./textForm');

import { Routes, Route } from "react-router-dom"; // 追加
import { Home } from "./home";
import { About } from "./about";
import { Contact } from "./contact";
import { NotFound } from "./notfound";
import { Footer } from "./footer";
import { TextForm } from './textForm';

import { AdminEntrance } from './admin/adminEntrance';
import { Sandbox } from './admin/sandbox';


console.log("app.js");

function App () {
  return (
    <div className="App">
      <h1>これはRoutesの外側のエレメント</h1>

      <Routes> {/*Routesで囲む*/}
        <Route path="/" element={ <Home /> } /> {}
        <Route path="/about" element={ <About /> } /> {}
        <Route path="/contact" element={ <Contact  message="Hello" /> } /> {}
        <Route path="/*" element={ <NotFound /> } /> {}

        <Route path="/about" element={ <About /> } /> {}
        <Route path="/admin" element={ <AdminEntrance /> } /> {}
        <Route path="/admin/newarticle" element={ <Sandbox/> } /> {}
        <Route path="/admin/sandbox" element={ <Sandbox/> } /> {}
      </Routes>

      <h1>これもRoutesの外側のエレメント</h1>
      <Footer/>
    </div>
  );
}

export default App;

const index = require('./index');

/*
class App extends React.Component {

  return (
    <div className="App">
      <h1>
        react-router-V6
      </h1>
    </div>
  );
}*/
/*
	constructor(props) {
		super(props);
		this.state = {employees: []};
	}*/

	//componentDidMount() {
	//
	//	client({method: 'GET', path: '/api/employees'}).done(response => {
	//		this.setState({employees: response.entity._embedded.employees});
	//	});
	//
	//}

//	render() {
	    /*
		return (
			<EmployeeList employees={this.state.employees}/>
		)
		*/

//		return (
//		    <TextForm name="aaa" placeholder="this is a placeholder" />
//		);
		//return ("TEST STRING on app.js")
//	}
//}
/*
class EmployeeList extends React.Component{
	render() {
		const employees = this.props.employees.map(employee =>
			<Employee key={employee._links.self.href} employee={employee}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Description</th>
					</tr>
					{employees}
				</tbody>
			</table>
		)
	}
}

class Employee extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.employee.firstName}</td>
				<td>{this.props.employee.lastName}</td>
				<td>{this.props.employee.description}</td>
			</tr>
		)
	}
}*/

/*
ReactDOM.render(
	<App />,
	document.getElementById('react')
)*/