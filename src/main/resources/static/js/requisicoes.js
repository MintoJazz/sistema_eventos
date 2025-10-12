// export async function query(tabela,query) {
//     try {
//         const response = await fetch(
//             "/query/" + tabela, {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'plain/text'
//                 },
//                 body: query
//             }
//         );
//         if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        
//         return response.json()
//     } catch (error) {
//         console.error('Error during fetch:', error);
//         throw error; // Re-throw the error for external handling
//     }
// }

