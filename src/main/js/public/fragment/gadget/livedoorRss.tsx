import React from "react";

export const LivedoorRss: React.FC = () => {

    return <div style={{width: '100%', border: 'none', overflow: 'hidden'}}>
        <iframe
            title="Blogroll Widget"
            src="/livedoor-rss.html"
            style={{
                width: '320px',
                height: '768px', // adjust height as needed
                backgroundColor: '#cccc88',
                border: 'none',
            }}
        />
    </div>;
    /* Default
        <script type="text/javascript">
        <!--
            var blogroll_channel_id = 356050;
        // -->
        </script>
        <script type="text/javascript" charset="utf-8" src="https://blogroll.livedoor.net/js/blogroll.js"></script>
        <link rel="stylesheet" type="text/css" href="https://blogroll.livedoor.net/css/default2.css" />
     */

    /* IFrame
        <iframe src="https://blogroll.livedoor.net/356050/iframe"
                allowtransparency="false"
                style={{background: "#cccc88", width: "320px", height: "512px"}}></iframe>
     */
}
