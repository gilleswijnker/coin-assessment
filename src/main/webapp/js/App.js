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
			return data.personOrCompany === "company" ? CompanyFactory(i, data) : PersonFactory(i, data)
		})
	}
}

function CompanyFactory(key, data) {
	return (
		<ListItem key={key}
			primaryText={data.companyName + ', ' + data.address}
			primaryTogglesNestedList={true}
			leftAvatar={<Avatar src='img/Company.png'/>}
			nestedItems={[
				ListItemFactory(key, 1, 'Name: ' + data.companyName),
				ListItemFactory(key, 2, 'Address: ' + data.address),
				ListItemFactory(key, 3, 'Phone number: ' + data.phoneNumber)
			]} 
		/>
	)
}

function PersonFactory(key, data) {
	return (
		<ListItem key={key}
			primaryText={data.lastName + ', ' + data.address}
			primaryTogglesNestedList={true}
			leftAvatar={<Avatar src='img/Person.png'/>}
			nestedItems={[
				ListItemFactory(key, 1, 'Name: ' + data.firstName + ' ' + data.lastName),
				ListItemFactory(key, 2, 'Address: ' + data.address),
				ListItemFactory(key, 3, 'Gender: ' + data.gender),
				ListItemFactory(key, 4, 'Phone number: ' + data.phoneNumber)
			]}
		/>
	)
}

function ListItemFactory(i, j, text) {
	const key = 10*(i+1) + j;
	return <ListItem key={key} primaryText={text} disabled={true}/>
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
