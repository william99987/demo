import './VideoCard.css';

const VideoCard = () => {
    return (
        <div className={"video-card"} style={{position:"relative", zIndex: 0}}>
            <video className={"video-background"} style={{ position: "relative", zIndex: 1}} autoPlay muted loop poster={"preview.jpg"}>
                <source src={require('../asset/cyber.mp4')} type={"video/mp4"}/>
            </video>
            <div className={"video-overlay"} style={{position:"absolute", top: "50%",
                left: "50%" ,zIndex: 2}}>
                <h5>Ready to head into the cyber 2080?</h5>
                <p>2080</p>
            </div>
        </div>
    )
}

// const VideoOverlay = () => {
//     return (
//         <div className={"video-overlay"}>
//             <h5 className={"card-title"}>Video Title</h5>
//             <p className={"card-text"}>Description</p>
//             <a href={"#"} className={"btn btn-dark"}>Watch now</a>
//         </div>
//     )
// }

export {VideoCard};