import { useEffect, useMemo, useRef, useState } from 'react';
import {
    ArrowDownToLine,
    ArrowUpFromLine,
    BriefcaseBusiness,
    Check,
    FileSpreadsheet,
    HardDriveUpload,
    RefreshCw,
    Search,
    UsersRound,
    X,
} from 'lucide-react';

const columns = [
    ['employeeCode', 'Employee code'],
    ['employeeName', 'Employee name'],
    ['projectCode', 'Project code'],
    ['projectName', 'Project name'],
    ['allocation', 'Allocation'],
    ['fte', 'FTE'],
    ['customerCode', 'Customer code'],
    ['customerName', 'Customer'],
    ['projectDUName', 'Project DU'],
    ['projectManagerName', 'Project manager'],
    ['projectCategory', 'Project category'],
    ['projectCategoryName', 'Category name'],
    ['wbsType', 'WBS type'],
    ['billingStatus', 'Billing status'],
    ['employeeLobName', 'Employee LOB'],
    ['band', 'Band'],
    ['subBand', 'Sub-band'],
    ['joiningDate', 'Joining date'],
    ['psa', 'PSA'],
];

async function responseError(response) {
    const message = await response.text();
    return message || `Request failed with status ${response.status}`;
}

function displayValue(key, value) {
    if (value === null || value === undefined || value === '') return '—';
    if ((key === 'allocation' || key === 'fte') && Number.isFinite(Number(value))) {
        return new Intl.NumberFormat(undefined, { maximumFractionDigits: 2 }).format(Number(value));
    }
    if ((key === 'employeeCode' || key === 'customerCode') && Number.isFinite(Number(value))) {
        // return new Intl.NumberFormat(undefined, { maximumFractionDigits: 0 }).format(Number(value));
        return Number(value);
    }
    return String(value);
}

function customerValue(customer, key) {
    if (key !== 'fte') return customer[key];

    const allocation = customer.allocation;
    if (allocation === null || allocation === undefined || allocation === '') return null;
    const numericAllocation = Number(allocation);
    return Number.isFinite(numericAllocation) ? numericAllocation / 100 : null;
}



export default function UserDashboard() {
    const inputRef = useRef(null);
    const [customers, setCustomers] = useState([]);
    const [selectedFile, setSelectedFile] = useState(null);
    const [query, setQuery] = useState('');
    const [loading, setLoading] = useState(true);
    const [uploading, setUploading] = useState(false);
    const [dragging, setDragging] = useState(false);
    const [error, setError] = useState('');
    const [notice, setNotice] = useState('');

    async function loadCustomers() {
        setLoading(true);
        setError('');
        try {
            const response = await fetch('/data');
            if (!response.ok) throw new Error(await responseError(response));
            const data = await response.json();
            if (!Array.isArray(data)) throw new Error('The /data endpoint did not return a list.');
            setCustomers(data);
        } catch (loadError) {
            setError(loadError.message || 'Could not load records. Check that the Spring API is running.');
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        loadCustomers();
    }, []);

    const filteredCustomers = useMemo(() => {
        const normalizedQuery = query.trim().toLowerCase();
        if (!normalizedQuery) return customers;
        return customers.filter((customer) =>
            columns.some(([key]) => String(customerValue(customer, key) ?? '').toLowerCase().includes(normalizedQuery)),
        );
    }, [customers, query]);

    const projectCount = new Set(customers.map((customer) => customer.projectCode).filter(Boolean)).size;
    const customerCount = new Set(customers.map((customer) => customer.customerName).filter(Boolean)).size;

    function chooseFile(file) {
        setNotice('');
        setError('');
        if (!file) return;
        if (!/\.(xlsx|xls)$/i.test(file.name)) {
            setSelectedFile(null);
            setError('Choose an Excel workbook in .xlsx or .xls format.');
            return;
        }
        setSelectedFile(file);
    }

    async function uploadFile(event) {
        event.preventDefault();
        if (!selectedFile || uploading) return;

        setUploading(true);
        setError('');
        setNotice('');
        const formData = new FormData();
        formData.append('file', selectedFile);

        try {
            const response = await fetch('/upload', { method: 'POST', body: formData });
            if (!response.ok) throw new Error(await responseError(response));
            const message = await response.text();
            setNotice(message || 'Workbook uploaded successfully.');
            setSelectedFile(null);
            if (inputRef.current) inputRef.current.value = '';
            await loadCustomers();
        } catch (uploadError) {
            setError(uploadError.message || 'The workbook could not be uploaded.');
        } finally {
            setUploading(false);
        }
    }

    function handleDrop(event) {
        event.preventDefault();
        setDragging(false);
        chooseFile(event.dataTransfer.files?.[0]);
    }

    return (
        <main className="dashboard-shell">
            <header className="topbar">
                <a className="brand" href="#top" aria-label="People and project dashboard home">
                    <span className="brand-mark"><BriefcaseBusiness size={18} strokeWidth={1.8} /></span>
                    <span>WORKFORCE <b>/</b> INTELLIGENCE</span>
                </a>
                <span className="topbar-label"><span className="status-dot" /> LIVE DATA</span>
            </header>

            <section className="intro" id="top">
                <div>
                    <p className="eyebrow">PEOPLE &amp; PROJECTS <span>·</span> OVERVIEW</p>
                    <h1>Allocation <em>roster</em></h1>
                    <p className="intro-copy">A clear view of people, client work, and project assignments.</p>
                </div>
                <button className="refresh-button" type="button" onClick={loadCustomers} disabled={loading}>
                    <RefreshCw size={16} className={loading ? 'spin' : ''} />
                    Refresh data
                </button>
            </section>

            <section className="metric-strip" aria-label="Roster summary">
                <div className="metric">
                    <span className="metric-icon metric-icon-green"><UsersRound size={17} /></span>
                    <span className="metric-label">ASSIGNMENTS</span>
                    <strong>{customers.length.toLocaleString()}</strong>
                </div>
                <div className="metric">
                    <span className="metric-icon metric-icon-yellow"><BriefcaseBusiness size={17} /></span>
                    <span className="metric-label">PROJECTS</span>
                    <strong>{projectCount.toLocaleString()}</strong>
                </div>
                <div className="metric">
                    <span className="metric-icon metric-icon-coral"><UsersRound size={17} /></span>
                    <span className="metric-label">CUSTOMERS</span>
                    <strong>{customerCount.toLocaleString()}</strong>
                </div>
            </section>

            <section className="upload-section" aria-labelledby="upload-heading">
                <div className="section-heading">
                    <div>
                        <p className="eyebrow">IMPORT</p>
                        <h2 id="upload-heading">Update the roster</h2>
                    </div>
                    <span className="format-note"><FileSpreadsheet size={15} /> XLSX or XLS</span>
                </div>
                <form onSubmit={uploadFile}>
                    <div
                        className={`drop-zone${dragging ? ' is-dragging' : ''}${selectedFile ? ' has-file' : ''}`}
                        onDragOver={(event) => { event.preventDefault(); setDragging(true); }}
                        onDragLeave={(event) => {
                            if (!event.currentTarget.contains(event.relatedTarget)) setDragging(false);
                        }}
                        onDrop={handleDrop}
                    >
                        <input
                            ref={inputRef}
                            className="file-input"
                            type="file"
                            accept=".xlsx,.xls"
                            aria-label="Select an Excel workbook"
                            onChange={(event) => chooseFile(event.target.files?.[0])}
                        />
                        <span className="upload-icon"><HardDriveUpload size={21} strokeWidth={1.7} /></span>
                        <div className="drop-copy">
                            <strong>{selectedFile ? selectedFile.name : 'Drop your workbook here'}</strong>
                            <span>{selectedFile ? `${(selectedFile.size / 1024 / 1024).toFixed(2)} MB · Ready to import` : 'or select an Excel file from your device'}</span>
                        </div>
                        {selectedFile ? (
                            <button
                                className="clear-file"
                                type="button"
                                aria-label="Remove selected file"
                                onClick={() => { setSelectedFile(null); if (inputRef.current) inputRef.current.value = ''; }}
                            ><X size={17} /></button>
                        ) : (
                            <button className="browse-button" type="button" onClick={() => inputRef.current?.click()}>
                                Browse files <ArrowDownToLine size={15} />
                            </button>
                        )}
                    </div>
                    <div className="upload-footer">
                        <span className="upload-hint">The first worksheet is imported; its header row is skipped.</span>
                        <button className="upload-button" type="submit" disabled={!selectedFile || uploading}>
                            {uploading ? <RefreshCw size={16} className="spin" /> : <ArrowUpFromLine size={16} />}
                            {uploading ? 'Uploading…' : 'Upload workbook'}
                        </button>
                    </div>
                </form>
                {error && <p className="feedback feedback-error" role="alert">{error}</p>}
                {notice && <p className="feedback feedback-success" role="status"><Check size={15} /> {notice}</p>}
            </section>

            <section className="roster-section" aria-labelledby="roster-heading">
                <div className="roster-heading-row">
                    <div>
                        <p className="eyebrow">DIRECTORY</p>
                        <h2 id="roster-heading">Employee assignments <span className="count-pill">{filteredCustomers.length}</span></h2>
                    </div>
                    <label className="search-box">
                        <Search size={17} />
                        <input
                            type="search"
                            value={query}
                            onChange={(event) => setQuery(event.target.value)}
                            placeholder="Search roster"
                            aria-label="Search employee assignments"
                        />
                        {query && <button type="button" aria-label="Clear search" onClick={() => setQuery('')}><X size={15} /></button>}
                    </label>
                </div>
                <div className="table-frame">
                    <table>
                        <thead>
                            <tr>{columns.map(([key, label]) => <th key={key} scope="col">{label}</th>)}</tr>
                        </thead>
                        <tbody>
                            {loading ? (
                                <tr><td className="table-message" colSpan={columns.length}>Loading roster…</td></tr>
                            ) : filteredCustomers.length === 0 ? (
                                <tr><td className="table-message" colSpan={columns.length}>
                                    {error ? 'Roster is unavailable until the API connection is restored.' : query ? 'No assignments match this search.' : 'No assignments yet. Upload a workbook to populate the roster.'}
                                </td></tr>
                            ) : filteredCustomers.map((customer, index) => (
                                <tr key={`${customer.employeeCode}-${customer.projectCode}-${index}`}>
                                    {columns.map(([key]) => {
                                        const value = customerValue(customer, key);
                                        return <td key={key} title={displayValue(key, value)}>{displayValue(key, value)}</td>;
                                    })}
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
                <p className="table-footnote">Showing {filteredCustomers.length.toLocaleString()} of {customers.length.toLocaleString()} assignments</p>
            </section>

            <footer className="page-footer"><span>WORKFORCE INTELLIGENCE</span><span>ROSTER DATA</span></footer>
        </main>
    );
}