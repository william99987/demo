import {AvatarCarousel} from "./AvatarCarousel";
import {React} from "react";
import {VideoCard} from "./VideoCard";

function ChatHome() {
    return (
        <div className={"vh-100 container-fluid"} style={{}}>
                <h1> Chatty </h1>
                <VideoCard/>
                <AvatarCarousel/>
        </div>);
};

export {ChatHome};