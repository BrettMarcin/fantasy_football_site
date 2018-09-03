import React, { Component } from 'react';
import Login from './Login';
import axios from 'axios';
import {
    BrowserRouter as Router,
    Route
} from 'react-router-dom'
import '../css/App.css';

class App extends Component {

    constructor(props) {
        super(props);

        this.state = {person: []};
    }

    componentDidMount() {
        this.UserList();
    }

    UserList() {
        axios.get('/api/')
            .then(res => {
                const persons = res.data;
                console.log(persons);
                //this.setState({ persons });
            })
    }

    render() {
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="App-title">React</h1>
        </header>
          <Router>
            <Route path="/login" component={Login}/>
          </Router>
      </div>
    );
  }
}

export default App;
