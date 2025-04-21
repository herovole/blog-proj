import React from "react";

export const LivedoorRss: React.FC = () => {

    React.useEffect(() => {
        // Inject the blogroll_channel_id variable
        const scriptVar = document.createElement('script');
        scriptVar.type = 'text/javascript';
        scriptVar.innerHTML = 'var blogroll_channel_id = 356050;';
        document.body.appendChild(scriptVar);

        // Inject the blogroll.js script
        const scriptSrc = document.createElement('script');
        scriptSrc.type = 'text/javascript';
        scriptSrc.charset = 'utf-8';
        scriptSrc.src = 'https://blogroll.livedoor.net/js/blogroll.js';
        document.body.appendChild(scriptSrc);

        // Inject the stylesheet
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.type = 'text/css';
        link.href = 'https://blogroll.livedoor.net/css/default2.css';
        document.head.appendChild(link);

        return () => {
            document.body.removeChild(scriptVar);
            document.body.removeChild(scriptSrc);
            document.head.removeChild(link);
        };
    }, []);

    return <div id="blogroll-widget" />;
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
