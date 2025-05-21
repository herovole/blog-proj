import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {SearchArticlesInput} from "../../../service/articles/searchArticlesInput";
import {SearchArticlesOutput} from "../../../service/articles/searchArticlesOutput";
import {HeadlinesMode, PublicArticleHeadlines} from "../../../public/fragment/articlelist/publicArticleHeadlines";
import {ArticleService} from "../../../service/articles/articleService";
import {OrderBy, OrderByEnum} from "../../../domain/articlelist/orderBy";

type ArticleListGadgetBodyProps = {
    directoryToIndividualPage: string;
    articleNumber?: number;
    topicTag?: string | null;
    countryTag?: string | null;
    orderBy?: OrderBy | null;
}

export const ArticleListGadgetBody: React.FC<ArticleListGadgetBodyProps> = ({
                                                                                directoryToIndividualPage,
                                                                                articleNumber = 4,
                                                                                topicTag = null,
                                                                                countryTag = null,
                                                                                orderBy = new OrderBy(OrderByEnum.LATEST_COMMENT_TIMESTAMP)
                                                                            }) => {
    const articleService: ArticleService = new ArticleService();
    const [output, setOutput] = React.useState(SearchArticlesOutput.empty());

    React.useEffect(() => {
        loadArticles().then();
    }, []);

    const loadArticles = async (): Promise<void> => {
        const MIN_DATE: Date = new Date("2025-01-01");
        const MAX_DATE: Date = new Date();
        const input: SearchArticlesInput = new SearchArticlesInput(
            articleNumber,
            1,
            true,
            MIN_DATE,
            MAX_DATE,
            "",
            topicTag,
            countryTag,
            orderBy,
            false
        );
        const output: SearchArticlesOutput = await articleService.searchArticles(input);
        if (output.isSuccessful()) {
            setOutput(output);
        } else {
            console.error(output.getMessage("article list retrieval"));
        }
    }

    return (
        <>
            <PublicArticleHeadlines
                mode={HeadlinesMode.IMAGE}
                articles={output.getArticleSummaryList()}
                directoryToIndividualPage={directoryToIndividualPage}
            />
        </>
    );
}