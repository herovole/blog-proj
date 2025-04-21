import React from "react";

type GadgetBaseProps = {
    children: React.ReactNode;
    subjectName: string;
    width?: string;
    isOpenByDefault?: boolean
}

export const GadgetBase: React.FC<GadgetBaseProps> = ({children, width="256px", subjectName, isOpenByDefault = true}) => {
    const [isOpen, setIsOpen] = React.useState<boolean>(isOpenByDefault);

    if (isOpen) {
        return (
            <div className="gadget-external" style={{width: width}}>
                <button className="gadget-close" onClick={function () {
                    setIsOpen(false);
                }}>{"▽" + subjectName + "ウィジェットを畳む"}
                </button>
                {children}
            </div>
        );
    } else {
        return (
            <div className="gadget-external" style={{width: width}}>
                <button className="gadget-close" onClick={function () {
                    setIsOpen(true);
                }}>{"▲" + subjectName + "ウィジェットを広げる"}
                </button>
            </div>
        );
    }
}
