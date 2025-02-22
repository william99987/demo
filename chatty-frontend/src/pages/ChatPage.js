import React, {useState, useEffect, useRef} from "react";
import axios from "axios";
import { Container } from "bootstrap";


const API_URL = "http://localhost:8080/chat/message";

const ChatPage = () => {
    const [sessionId, setSessionId] = useState(null);
    const [messages, setMessages] = useState([]);
    const [inputText, setInputText] = useState("");
    const [loading, setLoading] = useState(false);
    const chatEndRed = useRef(null);

    useEffect(() => {
        const startNewSession = async() => {
            try {
                const response = await axios.post('uri', { userId });
                setSessionId(response.data.id);
            } catch (error) {
                console.log("Error starting new session", error);
            }
        };

       startNewSession();
    }, []);


    return(
        <Container className={"chat-container"}>
            <Row>
                <Col>
                    <Card className={"chat-card"}>
                        <Card.Body>
                            <div className={"chat-box"}>

                            </div>
                            <Form>

                            </Form>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

        </Container>
    )
}