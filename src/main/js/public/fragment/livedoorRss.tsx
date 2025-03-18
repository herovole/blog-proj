import React from "react";

export const LivedoorRss: React.FC = () => {

    const script = "<!-- var blogroll_channel_id = 356050; // -->";
    return (<div>
            <script type="text/javascript">
                {script}
            </script>
            <script type="text/javascript" charSet="utf-8" src="https://blogroll.livedoor.net/js/blogroll.js"></script>
            <link rel="stylesheet" type="text/css" href="https://blogroll.livedoor.net/css/default2.css"/>
        </div>
    );

}
