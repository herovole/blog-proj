module.exports = {
    presets: [
        '@babel/preset-env',
        '@babel/preset-react',
        '@babel/preset-typescript' // Include others as needed
    ],
    plugins: [
        ["@babel/plugin-transform-runtime", {"helpers": true}]
    ],
};