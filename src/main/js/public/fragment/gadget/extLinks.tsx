import React from "react";
import {Link} from "react-router-dom";

export const ExtLinks: React.FC = () => {
    const [isOpen, setIsOpen] = React.useState<boolean>(true);

    const itemBlogWith2Net = <Link className="gadget-links-item-link" to="https://blog.with2.net/link/?id=2128848&cid=4281">人気ブログランキング</Link>

    if (isOpen) {
        return (
            <div className="gadget-links-external">
                <button className="gadget-links-close" onClick={function () {
                    setIsOpen(false);
                }}>▽参加リンクガジェットを畳む
                </button>
                <div className="gadget-links-title">当ブログの参加リンク</div>
                <div className="gadget-links-body">
                    <div className="gadget-links-item">{itemBlogWith2Net}</div>
                </div>
            </div>
        );
    } else {
        return (
            <div className="gadget-links-external">
                <button className="gadget-links-close" onClick={function () {
                    setIsOpen(true);
                }}>▲参加リンクガジェットを広げる
                </button>
            </div>
        );
    }
}
