import React, {useEffect} from "react";

export const LivedoorRss: React.FC = () => {
    useEffect(() => {
        // Set the blogroll channel ID dynamically
        window.blogroll_channel_id = 356050;

        // Create the script element for the blogroll script
        const script = document.createElement("script");
        script.type = "text/javascript";
        script.charset = "utf-8";
        script.src = "https://blogroll.livedoor.net/js/blogroll.js";
        document.body.appendChild(script);

        return () => {
            document.body.removeChild(script);
        };
    }, []);

    return (
        <div>
            {/* Stylesheet for livedoor RSS widget */}
            <link
                rel="stylesheet"
                type="text/css"
                href="https://blogroll.livedoor.net/css/default2.css"
            />
            <div id="blogroll-container"></div>
        </div>
    );
};

