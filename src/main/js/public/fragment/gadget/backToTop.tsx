import React from "react";

export const BackToTop: React.FC = () => {

    const handleOnClick = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        window.scrollTo({top: 0, behavior: "auto"});
    }

    return <button className="back-to-top" onClick={handleOnClick}>
        ▲ ページトップへ
    </button>;

}