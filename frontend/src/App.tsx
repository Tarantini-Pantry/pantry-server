import './App.scss';
import {ItemList} from './components/ItemList';
import {Login} from "./components/Login";
import {useState} from "react";
import {User} from "./types/User";
import Cookies from "js-cookie";

function App() {
  const [user, setUser] = useState({})
  const cookie = Cookies.get("user_session");

  const setUserFromServer = (user: User) => {
    setUser(user)
  }
  return (
    <div className="App">
      { cookie != null ? <ItemList/> : <Login setUser={setUserFromServer}/> }
    </div>
  );
}

export default App;
