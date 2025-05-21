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
    const [output, setOutput] = React.useState<SearchArticlesOutput | null>(SearchArticlesOutput.empty());

    React.useEffect(() => {
        loadArticles().then(setOutput);
    }, []);

    const loadArticles = async (): Promise<SearchArticlesOutput | null> => {
        const input: SearchArticlesInput = new SearchArticlesInput(
            articleNumber,
            1,
            true,
            null,
            null,
            "",
            topicTag,
            countryTag,
            orderBy,
            false
        );
        const output: SearchArticlesOutput = await articleService.searchArticles(input);
        if (output.isSuccessful()) {
            return output;
        } else {
            console.error(output.getMessage("gadget article list retrieval"));
            return null;
        }
    }

    if (output != null && output.getLength() > 0) {
        return (
            <PublicArticleHeadlines
                mode={HeadlinesMode.IMAGE}
                articles={output.getArticleSummaryList()}
                directoryToIndividualPage={directoryToIndividualPage}
            />
        );
    } else {
        return <div>loading...</div>
    }
}