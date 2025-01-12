import logo from './logo.svg';
import './App.css';
import {Sidebar} from './pages/Sidebar'

function App() {
  return (
    <div className="App">
        <div className="row">
            <div className={"col-3"}>
                <Sidebar/>
            </div>
            <div className="col-9">
                <h1>Chatty</h1>
            </div>
        </div>
    </div>
  );
}

export default App;
