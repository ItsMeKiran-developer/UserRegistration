import React, {Component}  from 'react';
import '../resources/static/App.css';
 
class App extends Component {
  render() {
    return (
      <div className="App">
        <AppInfo/>
      </div>
    )
  }
}

class AppInfo extends Component{
  render() {
    return (
      <div className="AppInfo">
        Learning A Full Stack Web Application
      </div>
    );
  }
}

function AppDetails (){
return (
      <div className="AppInfo">
       Full Stack Web Application Details
      </div>
    );
 }
 
export default App;
