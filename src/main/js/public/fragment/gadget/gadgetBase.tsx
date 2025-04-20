import React from "react";

type GadgetBaseProps = {
    children: React.ReactNode;
    subjectName: string;
    isOpenByDefault?: boolean
}

export const GadgetBase: React.FC<GadgetBaseProps> = ({children, subjectName, isOpenByDefault = true}) => {
    const [isOpen, setIsOpen] = React.useState<boolean>(isOpenByDefault);

    if (isOpen) {
        return (
            <div className="gadget-external">
                <button className="gadget-close" onClick={function () {
                    setIsOpen(false);
                }}>{"▽" + subjectName + "ガジェットを畳む"}
                </button>
                {children}
            </div>
        );
    } else {
        return (
            <div className="gadget-external">
                <button className="gadget-close" onClick={function () {
                    setIsOpen(true);
                }}>{"▲" + subjectName + "ガジェットを広げる"}
                </button>
            </div>
        );
    }
}
