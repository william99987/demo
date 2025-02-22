import logo from './logo.svg';
import './App.css';
import {Sidebar} from './pages/Sidebar'
import {ChatHome} from "./pages/ChatHome"

function App() {
  return (
    <div className="App">
        <div className="row g-0">
            <div className={"col-3"}>
                <Sidebar />
            </div>
            <div className={"col-9 bg-black"}>
                <ChatHome />
            </div>
        </div>
    </div>
  );
}

export default App;
