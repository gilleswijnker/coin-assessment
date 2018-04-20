'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import {List, ListItem} from 'material-ui/List';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Avatar from 'material-ui/Avatar';
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
				<MuiThemeProvider>
					<List>
						<SearchResults results={this.state.result}/>
					</List>
				</MuiThemeProvider>
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
			result => {
				const json = result.json();
				if (!result.ok) {
					return json.then(err => {throw err.errMessage})
				}
				return json;
			}
		).then(
			data => {
				this.setState({
					result: JSON.parse(data.result),
					page: data.pageNumber,
					totalPages: data.totalPages,
					totalResults: data.totalElements
				})
			}
		).catch(
			err => console.log(err)
		);
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

class ResultElement extends React.Component {
	constructor(props) {
		super(props);
		this.state = {hidden: true};
	}

	onClick() {
		this.setState({hidden: !this.state.hidden});
	}
}

class Company extends ResultElement {
	render() {
		return (
			<ListItem value={this.props.key}
				primaryText={this.props.data.companyName + ', ' + this.props.data.address}
				primaryTogglesNestedList={true}
				leftAvatar={<Avatar src='img/Company.png'/>}
				nestedItems={[
					<ListItem primaryText={'Name: ' + this.props.data.companyName} disabled={true}/>,
					<ListItem primaryText={'Address: ' + this.props.data.address} disabled={true}/>,
					<ListItem primaryText={'Phone number: ' + this.props.data.phoneNumber} disabled={true}/>
				]} 
			/>
		)
	}
}

class Person extends ResultElement {
	render() {
	return (
			<ListItem value={this.props.key}
				primaryText={this.props.data.lastName + ', ' + this.props.data.address}
				primaryTogglesNestedList={true}
				leftAvatar={<Avatar src='img/Person.png'/>}
				nestedItems={[
					<ListItem primaryText={'Name: ' + this.props.data.firstName + ' ' + this.props.data.lastName} disabled={true}/>,
					<ListItem primaryText={'Address: ' + this.props.data.address} disabled={true}/>,
					<ListItem primaryText={'Gender: ' + this.props.data.gender} disabled={true}/>,
					<ListItem primaryText={'Phone number: ' + this.props.data.phoneNumber} disabled={true}/>
				]}
			/>
		)
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
				<button onClick={() => this.togglePage(-1)}>Vorige...</button>
				<button onClick={() => this.togglePage(1)}>Volgende...</button>
				<p>Pagina: {this.props.page} van {this.props.totalPages}. Totaal resultaten: {this.props.totalResults}</p>
				<form onSubmit={(e) => this.changePageNumber(e)}>
					Ga naar pagina: <input type="number" defaultValue="1" min="1" max={this.props.totalPages} onChange={(e) => this.handleChange(e)}/>
					<input type="submit" value="Ga!"/>
				</form>
			</div>
		)
	}

	togglePage(i) {
		const newPage = this.state.page + i;
		if (newPage > 0 && newPage <= this.props.totalPages) {
			this.setState({page: newPage}, this.changePageNumber);
		}1
	}

	changePageNumber(event) {
		if (event != null) {
			event.preventDefault();
		}
		this.props.onSubmit(this.state.page);
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)
