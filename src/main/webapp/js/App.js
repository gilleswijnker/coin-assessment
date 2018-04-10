'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import '../css/search.css';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			result: [],
			searchValue: '',
			page: 1,
			totalPages: 0,
			totalResults: 0
		};
	}

	handleChange(event) {
		const safeSearchValue = encodeURIComponent(event.target.value);
		this.setState({searchValue: safeSearchValue}, this.searchData);
	}

	render() {
		return (
			<div>
				<form onSubmit={(e) => this.onSubmit(e)}>
					<input className="search" type="text" onChange={(e) => this.handleChange(e)} />
					<input type="submit" value="Zoek"/>
				</form>
				<SearchResults results={this.state.result}/>
				<PageInformation page={this.state.page} totalPages={this.state.totalPages} totalResults={this.state.totalResults} onSubmit={(page) => this.changePageNumber(page)}/>
			</div>
		)
	}

	onSubmit(event) {
		event.preventDefault();
		this.searchData();
	}

	searchData() {
		fetch(
			'/api/query?searchvalue=' + this.state.searchValue + "&page=" + this.state.page
		).then(
			result => {return result.json()}
		).then(
			data => {
				console.log(data);
				this.setState({
					result: JSON.parse(data.result),
					page: data.pageNumber,
					totalPages: data.totalPages,
					totalResults: data.totalElements
				})
			}
		)
	}

	changePageNumber(gotoPage) {
		if (gotoPage < 1 || gotoPage > this.state.totalPages) return;
		this.setState({page: gotoPage}, this.searchData);
	}
}

class SearchResults extends React.Component {
	render() {
		return this.props.results.map((data, i) => {
			return data.personOrCompany === "company" ? <Company key={i} data={data}/> : <Person key={i} data={data}/>
		})
	}
}

class Company extends React.Component {
	constructor(props) {
		super(props);
		this.state = {hidden: true};
	}

	render() {
		return (
			<div className='entity company' onClick={() => this.onClick()}>
				<p>Company:</p>
				<p hidden={this.state.hidden}>{this.props.data.id}</p>
				<p>{this.props.data.companyName}</p>
				<p hidden={this.state.hidden}>{this.props.data.address}</p>
				<p hidden={this.state.hidden}>{this.props.data.phoneNumber}</p>
			</div>
		)
	}

	onClick() {
		this.setState({hidden: !this.state.hidden});
	}
}

class Person extends React.Component {
	constructor(props) {
		super(props);
		this.state = {hidden: true};
	}

	render() {
		return (
			<div className='entity person' onClick={() => this.onClick()}>
				<p>Person:</p>
				<p hidden={this.state.hidden}>{this.props.data.id}</p>
				<p hidden={this.state.hidden}>{this.props.data.firstName}</p>
				<p>{this.props.data.lastName}</p>
				<p hidden={this.state.hidden}>{this.props.data.address}</p>
				<p hidden={this.state.hidden}>{this.props.data.gender}</p>
				<p hidden={this.state.hidden}>{this.props.data.phoneNumber}</p>
			</div>
		)
	}

	onClick() {
		this.setState({hidden: !this.state.hidden});
	}
}

class PageInformation extends React.Component {
	constructor(props) {
		super(props);
		this.state = {page: 1};
	}

	handleChange(event) {
		this.setState({page: event.target.value});
	}

	render() {
		return (
			<div className='pageinfo'>
				<p>Pagina: {this.props.page} van {this.props.totalPages}. Totaal resultaten: {this.props.totalResults}</p>
				<form onSubmit={(e) => this.changePageNumber(e)}>
					Ga naar pagina: <input type="number" defaultValue="1" min="1" max={this.props.totalPages} onChange={(e) => this.handleChange(e)}/>
					<input type="submit" value="Ga!"/>
				</form>
			</div>
		)
	}

	changePageNumber(event) {
		event.preventDefault();
		this.props.onSubmit(this.state.page);
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)
