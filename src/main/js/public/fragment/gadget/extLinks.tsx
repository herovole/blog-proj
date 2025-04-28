import React from "react";
import {Link} from "react-router-dom";

export const ExtLinks: React.FC = () => {

    const itemBlogWith2Net = <Link className="gadget-links-item-link"
                                   to="https://blog.with2.net/link/?id=2128848&cid=4281">人気ブログランキング</Link>;

    const item2channelerCom = <Link className="gadget-links-item-link"
                                    to="https://2channeler.com/">ねらーアンテナ</Link>
    const itemLivdirComKaigaiAntenna = <Link className="gadget-links-item-link"
                                             to="https://livdir.com/kaigai-antenna/">シンプル海外の反応アンテナ</Link>
    const itemKaihanAntennaCom = <Link className="gadget-links-item-link"
                                       to="https://kaihan-antenna.com/">海外の反応アンテナ.com</Link>;
    const itemKaikenAtnaJp = <Link className="gadget-links-item-link"
                                   to="https://kaiken.atna.jp/">海外反応研究会</Link>;
    const itemYakutenaCom = <Link className="gadget-links-item-link" to="https://www.yakutena.com/">ヤクテナ</Link>;

    return (
        <div>
            <div className="gadget-links-title">登録アンテナサイト</div>
            <div className="gadget-links-body">
                <div className="gadget-links-item">{itemBlogWith2Net}</div>
            </div>
            <div className="gadget-links-title">申請中アンテナサイト</div>
            <div className="gadget-links-body">
                <div className="gadget-links-item">{itemLivdirComKaigaiAntenna}</div>
                <div className="gadget-links-item">{item2channelerCom}</div>
                <div className="gadget-links-item">{itemKaihanAntennaCom}</div>
                <div className="gadget-links-item">{itemKaikenAtnaJp}</div>
                <div className="gadget-links-item">{itemYakutenaCom}</div>
            </div>
        </div>
    );
}
