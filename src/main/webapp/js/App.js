'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
// const client = require('./client');

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			result: [],
			searchValue: ''
	};
	this.handleChange = this.handleChange.bind(this);
	}

	handleChange(event) {
		console.log("handling change");
		this.setState({searchValue: event.target.value});
		this.searchData(this.state.searchValue);
	}

	render() {
		console.log("rendering");
		return (
			<div>
				<input type="text" onChange={this.handleChange} />
				<button type="button" onClick={ () => this.searchData(this.state.searchValue) }>Klik mij!</button>
				<SearchResults results={this.state.result}/>
			</div>
		)
	}

	searchData(query) {
		console.log("searching");
		fetch(
			'/api/query?searchvalue=' + this.state.searchValue
		).then(
			result => {return result.json()}
		).then(
			data => this.setState({result: data})
		)
	}
}

class SearchResults extends React.Component {
	render() {
		const p = this.props.results.map((data, i) => {
			return data.personOrCompany ? <Company key={i} data={data}/> : <Person key={i} data={data}/>
		})
		
		return (
			p
		)
	}
}

class Company extends React.Component {
	render() {
		return (
			<p>{this.props.data.companyName} {this.props.data.address} {this.props.data.phoneNumber}</p>
		)
	}
}

class Person extends React.Component{
	render() {
		return (
			<p>{this.props.data.firstName} {this.props.data.lastName} {this.props.data.address} {this.props.data.phoneNumber}</p>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)
