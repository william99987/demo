import { Button } from "bootstrap";
import {useState, React} from "react";
import axios from "axios";

const SigninModal = ({ showSigninModal, toggleSigninModal}) => {
    return (
        <div className={"container"}>
            <div className={`modal ${showSigninModal ? 'd-block' : 'd-none'}` }>
                <div className={"modal-dialog"} role={"document"}>
                    <div className={"modal-content"}>
                        <div className={"modal-header"}>
                            <h5 className={"modal-title"} id={"signinModalLabel"}Sign in/>
                            <button type={"button"} className={"close"} onClick={toggleSigninModal}>
                                <span aria-hidden={"true"}>&times;</span>
                            </button>
                        </div>
                        <div className={"modal-body"}>
                            <form>
                                <div className={"mb-3"}>
                                    <label htmlFor={"email"} className={"form-label"}>Email address</label>
                                    <input type={"email"} className={"form-control"} id={"email"} placeholder={"Enter your email"} />
                                </div>
                                <div className={"mb-3"}>
                                    <label htmlFor={"password"} className={"form-label"}>Password</label>
                                    <input type={"password"} className={"form-control"} id={"password"} placeholder={"Enter your password"} />
                                </div>
                                <div className={"mb-3 form-check"}>
                                    <input type={"checkbox"} className={"form-check-input"} id={"rememberMe"} />
                                    <label className={"form-check-label"} htmlFor={"rememberMe"}>Remember me</label>
                                </div>
                                <button type={"submit"} className={"btn btn-dark"}>Submit</button>
                            </form>
                        </div>
                        <div className={"modal-footer"}>
                            <button type={"button"} className={"btn btn-dark"} onClick={toggleSigninModal}>Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

const SignupModal = ({ showSignupModal, toggleSignupModal}) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [username, setUsername] = useState("");
    const [message, setMessage] = useState("");

    const handleChange = (e) => {
        if (e.target.id === "email") {
            setEmail(e.target.value);
        }
        else if (e.target.id === "password") {
            setPassword(e.target.value);
        }
        else {
            setUsername(e.target.value);
        }
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        const requestBody = {
            username: username,
            password: password,
            email: email
        };
        try {
            const response = axios.post("/demo-0.0.1/user/register", requestBody);
            setMessage("User created successfully");
        } catch (error) {
            if(error.response){
                setMessage('Error: ${error.response.data}');
            } else {
                setMessage('An error occurred. Please try again later.');
            }
        };

        setEmail("");
        setPassword("");
    }

    return (
        <div className={"container"}>
            <div className={`modal ${showSignupModal ? 'd-block' : 'd-none'}` }>
                <div className={"modal-dialog"} role={"document"}>
                    <div className={"modal-content"}>
                        <div className={"modal-header"}>
                            <h5 className={"modal-title"} id={"signupModalLabel"}Sign Up/>
                            <button type={"button"} className={"close"} onClick={toggleSignupModal}>
                                <span aria-hidden={"true"}>&times;</span>
                            </button>
                        </div>
                        <div className={"modal-body"}>
                            <form>

                                <div className={"mb-3"}>
                                    <label htmlFor={"username"} className={"form-label"}>User name</label>
                                    <input type={"username"} className={"form-control"} id={"username"}
                                           onChange={handleChange} placeholder={"Enter your Username"}/>
                                </div>

                                <div className={"mb-3"}>
                                    <label htmlFor={"email"} className={"form-label"}>Email address</label>
                                    <input type={"email"} className={"form-control"} id={"email"}
                                           onChange={handleChange} placeholder={"Enter your email"}/>
                                </div>
                                <div className={"mb-3"}>
                                    <label htmlFor={"password"} className={"form-label"}>Password</label>
                                    <input type={"password"} className={"form-control"} id={"password"}
                                           onChange={handleChange} placeholder={"Enter your password"}/>
                                </div>
                                <button type={"submit"} className={"btn btn-dark"} onClick={handleSubmit}>Submit
                                </button>
                            </form>
                        </div>
                        <div className={"modal-footer"}>
                            <button type={"button"} className={"btn btn-dark"} onClick={toggleSignupModal}>Close
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

const Sidebar = () => {

    const [showSigninModal, setShowSigninModal] = useState(false);
    const [showSignupModal, setShowSignupModal] = useState(false);
    const toggleSigninModal = () => setShowSigninModal(!showSigninModal);
    const toggleSignupModal = () => setShowSignupModal(!showSignupModal);

    return (
    <div className={"bg-dark container"}>
        <h2>Chatty</h2>
        <button type={"button"} className={"btn btn-dark mb-4"}>Create a Chatty</button>
        <div className={"d-flex flex-column"}>
            <button className={"btn btn-dark mb-2"}>Discover</button>
            <button className={"btn btn-dark mb-2"}>Search</button>
            <button className={"btn btn-dark mb-2"}>Memory</button>
        </div>
        <button className={"btn btn-dark mt-5"} onClick={toggleSigninModal}>Sign in</button>
        <button className={"btn btn-dark mt-5"} onClick={toggleSignupModal}>Sign up</button>
        <SigninModal showSigninModal={showSigninModal} toggleSigninModal={toggleSigninModal}/>
        <SignupModal showSignupModal={showSignupModal} toggleSignupModal={toggleSignupModal}/>
    </div>);
};

export {Sidebar, SigninModal};