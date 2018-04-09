const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

console.log("TEST");

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {result: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/query'}).done(response => {
			this.setState({result: response});
		});
	}

	render() {
		return (
			<EmployeeList employees={this.state.result}/>
		)
	}
}

class EmployeeList extends React.Component{
	render() {
		return(
            <p>
                {this.state.result}
            </p>
        )
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)